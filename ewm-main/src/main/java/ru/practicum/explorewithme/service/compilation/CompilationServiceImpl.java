package ru.practicum.explorewithme.service.compilation;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.explorewithme.dto.input.create.NewCompilationDto;
import ru.practicum.explorewithme.dto.input.update.UpdateCompilationRequest;
import ru.practicum.explorewithme.dto.output.CompilationDto;
import ru.practicum.explorewithme.exeption.NotFoundException;
import ru.practicum.explorewithme.model.Compilation;
import ru.practicum.explorewithme.model.Event;
import ru.practicum.explorewithme.repository.CompilationRepository;
import ru.practicum.explorewithme.repository.EventRepository;

import org.springframework.transaction.annotation.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CompilationServiceImpl implements AdminCompilationService, PublicCompilationService {
    private final CompilationRepository compilationRepository;

    private final EventRepository eventRepository;

    private final CompilationMapper compilationMapper;

    @Override
    @Transactional
    public CompilationDto createCompilation(NewCompilationDto newCompilationDto) {
        Set<Event> events;
        if (newCompilationDto.getEvents() == null || newCompilationDto.getEvents().isEmpty()) {
            events = new HashSet<>();
        } else {
            events = new HashSet<>(eventRepository.findAllById(newCompilationDto.getEvents()));
        }
        final Compilation newCompilation = compilationMapper.fromDto(newCompilationDto, events);

        compilationRepository.save(newCompilation);

        return compilationMapper.toDto(newCompilation);
    }

    @Override
    @Transactional
    public CompilationDto updateCompilationById(Long compId, UpdateCompilationRequest compilationDto) {
        if (!compilationDto.isSomeChange()) {
            throw new IllegalArgumentException("Data to update is empty.");
        }

        Compilation compilation = compilationRepository.findById(compId)
                .orElseThrow(() -> new NotFoundException(compId, Compilation.class));

        if (compilationDto.isPinned()) {
            compilation.setPinned(compilationDto.getPinned());
        }
        if (compilationDto.isTittle()) {
            compilation.setTitle(compilationDto.getTitle());
        }
        if (compilationDto.isEvents()) {
            compilation.getEvents().clear();
            List<Event> events = eventRepository.findAllById(compilationDto.getEvents());
            compilation.getEvents().addAll(events);
        }
        return compilationMapper.toDto(compilation);
    }

    @Override
    @Transactional
    public Boolean deleteCompilationById(Long compId) {
        if (compilationRepository.existsById(compId)) {
            compilationRepository.deleteById(compId);
        } else {
            throw new NotFoundException(compId, Compilation.class);
        }
        return true;
    }

    @Override
    public List<CompilationDto> getPinnedCompilations(Boolean pinned, Integer from, Integer size) {
        Pageable pageable = PageRequest.of(from > 0 ? from / size : 0, size);

        return compilationRepository.findByPinned(pinned, pageable)
                .stream()
                .map(compilationMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<CompilationDto> getCompilations(Integer from, Integer size) {
        Pageable pageable = PageRequest.of(from > 0 ? from / size : 0, size);

        return compilationRepository.findAll(pageable)
                .stream()
                .map(compilationMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public CompilationDto findCompilationById(Long compId) {
        return compilationRepository.findById(compId)
                .map(compilationMapper::toDto)
                .orElseThrow(() -> new NotFoundException(compId, Compilation.class));
    }
}
